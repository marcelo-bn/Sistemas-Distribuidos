import zmq, sys

context = zmq.Context()
s = context.socket(zmq.SUB)          

HOST = sys.argv[1] if len(sys.argv) > 1 else "localhost"
PORT = sys.argv[2] if len(sys.argv) > 2 else "50009"

p = "tcp://"+ HOST +":"+ PORT        
s.connect(p)                         
s.setsockopt(zmq.SUBSCRIBE, b"TIME")  

for i in range(5):  
  time = s.recv()   
  print(time.decode())