#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import time
from random import random, randint, seed
from flask import Flask, jsonify
from flask import abort
from flask import make_response
from flask import request
from flask import url_for
from flask_httpauth import HTTPBasicAuth
import json
import subprocess
import requests
import sys

# Criando objeto da classe HTTP
auth = HTTPBasicAuth()

# Criando objeto da classe Flask
app = Flask(__name__)

# Definie se a aplicação é coordenadora
coordenador = False

# Semente para número aleatório
semente = 1

# Lista de réplicas
replicas = []

# Lista de transações temporárias
transacao_temp = []

# Lista de ações
acoes = []

# Lista de contas
contas = [
    {
        "numero": "1234",
        "saldo": "100,00"
    },
    {
        "numero": "4345",
        "saldo": "50,00"
    },
    {
        "numero": "5678",
        "saldo": "250,00"
    }
]


# Retorna lista de contas
@app.route('/contas', methods=['GET'])
def obtem_contas():
    return jsonify({'contas': contas})


# Retorna lista de répicas
@app.route('/contas/replicas', methods=['GET'])
def lista_replicas():
    if coordenador:
        return make_response(jsonify({'replicas': replicas}), 200)
    else:
        return make_response(jsonify('Este processo não é o coordenador.'), 403)


# Retorna lista de ações realizadas
@app.route('/contas/acoes', methods=['GET'])
def lista_acoes():
    return jsonify({'acoes': acoes})


# Recebe lista de réplicas
@app.route('/contas/replicas', methods=['POST'])
def armazena_replicas():
    global coordenador, replicas
    coordenador = True

    replicas = request.json.get('replicas')

    return make_response(jsonify('Aplicações cadastradas!'), 200)


# Recebe valor para atuar como semente de número aleatório
@app.route('/contas/semente', methods=['POST'])
def recebe_semente():
    global semente
    semente = int(request.json.get('seed'))
    if semente < 0:
        return make_response(jsonify('Semente inválida'), 400)
    else:
        seed(semente)
        return make_response(jsonify('Semente recebida!'), 200)


# Recebe dados para realizar uma transação
@app.route('/contas/acao', methods=['POST'])
def realiza_acao():
    # Captura informações recebidas do cliente
    operacao = request.json.get('operacao')
    valor = request.json.get('valor')
    conta = request.json.get('conta')
    id = request.json.get('id')

    # Armazena essas informações temporariamente
    transacao = {
        'operacao': request.json.get('operacao'),
        'valor': request.json.get('valor'),
        'conta': request.json.get('conta'),
        'id': request.json.get('id'),
    }
    transacao_temp.append(transacao)

    transacao_json = {'operacao': operacao, 'valor': valor, 'conta': conta, 'id': id}

    if coordenador:

        url1 = replicas[0]['endpoint'] + '/acao'
        url2 = replicas[1]['endpoint'] + '/acao'

        p1 = requests.post(url1, json=transacao_json)
        p2 = requests.post(url2, json=transacao_json)

        id_json = {'id': id}

        if p1.status_code == 200 and p2.status_code == 200:
            p3 = requests.put(replicas[0]['endpoint'] + '/decisao', json=id_json)
            p4 = requests.put(replicas[1]['endpoint'] + '/decisao', json=id_json)

            if operacao_func(id) != 'nok':

                acoes_aux = {
                    'id': id,
                    'status': 'success'
                }
                acoes.append(acoes_aux)

                return make_response(jsonify('success'), 201)

            else:
                acoes_aux = {
                    'id': id,
                    'status': 'success'
                }
                acoes.append(acoes_aux)

                return make_response(jsonify('Sem saldo'), 403)


        else:
            p5 = requests.delete(replicas[0]['endpoint'] + '/decisao', json=id_json)
            p6 = requests.delete(replicas[1]['endpoint'] + '/decisao', json=id_json)

            acoes_aux = {
                'id': id,
                'status': 'fail'
            }
            acoes.append(acoes_aux)

            transacao_temp.clear()

            return make_response(jsonify('fail'), 403)

    else:
        x = randint(1, 10)

        if (x <= 7):
            return make_response('yes', 200)
        else:
            return make_response('no', 403)


# Realiza transação
@app.route('/contas/decisao', methods=['PUT'])
def decisao_yes():
    identificador = request.json.get('id')
    resultado1 = [resultado1 for resultado1 in transacao_temp if resultado1['id'] == identificador]

    if coordenador:
        return make_response(jsonify('Processo coordenador'), 403)

    elif len(resultado1) == 0:
        return make_response(jsonify('Identificador não existe.'), 404)

    else:

        identificador = request.json.get('id')
        operacao_func(identificador)

        acoes_aux = {
            'id': identificador,
            'status': 'success'
        }
        acoes.append(acoes_aux)

        return make_response(jsonify('ack'), 200)


# Não realiza transação
@app.route('/contas/decisao', methods=['DELETE'])
def decisao_no():
    identificador = request.json.get('id')
    resultado1 = [resultado1 for resultado1 in transacao_temp if resultado1['id'] == identificador]

    if coordenador:
        return make_response(jsonify('Processo coordenador'), 403)

    elif len(resultado1) == 0:
        return make_response(jsonify('Identificador não existe.'), 404)

    else:

        transacao_temp.remove(resultado1[0])

        transacao_temp.clear()
        acoes_aux = {
            'id': identificador,
            'status': 'fail'
        }
        acoes.append(acoes_aux)
        return make_response(jsonify('ack'), 200)


# Apaga lista de réplicas
@app.route('/contas/replicas', methods=['DELETE'])
def apaga_replicas():
    if len(replicas) > 0:
        replicas.clear()
        global coordenador
        coordenador = False
        return make_response(jsonify('Lista deletada. Processo não é mais coordenador.'), 200)
    else:
        return make_response(jsonify('Ainda não foi carregada nenhuma lista.'), 403)


# Realiza operacão da transação
def operacao_func(id_operacao):
    resultado1 = [resultado1 for resultado1 in transacao_temp if resultado1['id'] == id_operacao]
    conta_operacao = resultado1[0]['conta']
    resultado2 = [resultado2 for resultado2 in contas if resultado2['numero'] == conta_operacao]

    if resultado1[0]['operacao'] == 'debito':
        a = float(resultado2[0]['saldo'].replace(',', '.'))
        b = float(resultado1[0]['valor'].replace(',', '.'))
        c = a - b

        if c >= 0.0:
            resultado2[0]['saldo'] = str(c)
            f = resultado2[0]['saldo'].replace('.', ',')
            resultado2[0]['saldo'] = f
        else:
            return 'nok'

    else:
        a = float(resultado2[0]['saldo'].replace(',', '.'))
        b = float(resultado1[0]['valor'].replace(',', '.'))
        c = a + b

        resultado2[0]['saldo'] = str(c)
        f = resultado2[0]['saldo'].replace('.', ',')
        resultado2[0]['saldo'] = f


# Erro 404 HTTP se tentar acessar um recurso que nao existe
@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'erro': 'Recurso Nao encontrado'}), 404)


if __name__ == "__main__":
    print("Aplicação no ar!")
    app.run(host='0.0.0.0', debug=True, port=int(sys.argv[1]))
