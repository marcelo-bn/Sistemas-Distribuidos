import zmq
import time

context = zmq.Context()

s = context.socket(zmq.PUB)
HOST = "*"
PORT = "50009"

p = "tcp://" + HOST + ":" + PORT
s.bind(p)

while True:
    time.sleep(5)
    msg = str("TIME " + time.asctime())
    s.send(msg.encode())
    print(msg)