import zmq
import threading

context = zmq.Context()

HOST = "*"
PORT1 = "50007"
PORT2 = "50008"

p1 = "tcp://"+ HOST +":"+ PORT1 
p2 = "tcp://"+ HOST +":"+ PORT2 

s  = context.socket(zmq.REP)    # Criando um socket reply
s.bind(p1)                      # conectando o socket na porta p1
s.bind(p2)                      # conectando o socket na porta p2

while True:
  message = s.recv()         
  sMsg = message.decode()
  if not "stop" in sMsg:       
     print("> Mensagem recebida: {}".format(sMsg))
     s.send(str("Recebi sua mensagem!").encode('utf-8'))
  else:
    break

s.close()