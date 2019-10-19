import sys
import zmq

context = zmq.Context()

HOST = sys.argv[1] if len(sys.argv) > 1 else "localhost"
PORT = sys.argv[2] if len(sys.argv) > 2 else "50007"
p2 = "tcp://"+ HOST +":"+ PORT 
s  = context.socket(zmq.REQ)
s.connect(p2)
while True:
    word = input("> Escreva sua mensagem: ")
    bWord = word.encode('utf-8')
    s.send(bWord)
    if "stop"in word:
        break
    message = s.recv()
    sMessage = message.decode()
    print("> Mensagem recebida: {}".format(sMessage)) # print result