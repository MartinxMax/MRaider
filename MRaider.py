# @Мартин.
import serial,argparse,textwrap,sys,os,datetime
from loguru import logger
Version = "@Мартин. MRaider V1.0.0"
 

Logo = f'''
██████╗      █████╗     ██╗    ██████╗     ███████╗    ██████╗ 
██╔══██╗    ██╔══██╗    ██║    ██╔══██╗    ██╔════╝    ██╔══██╗
██████╔╝    ███████║    ██║    ██║  ██║    █████╗      ██████╔╝
██╔══██╗    ██╔══██║    ██║    ██║  ██║    ██╔══╝      ██╔══██╗
██║  ██║    ██║  ██║    ██║    ██████╔╝    ███████╗    ██║  ██║
╚═╝  ╚═╝    ╚═╝  ╚═╝    ╚═╝    ╚═════╝     ╚══════╝    ╚═╝  ╚═╝
                                                                                                
                                        Github ==> https://github.com/MartinxMax
                                        {Version}
'''

CONF = '''
[+] ---------------------------------------
| COM:{}\tBaudrate:{}\tBytesize:{}
| Parity:{}\tStopbits:{}\tTimeout:{}
| Rtscts:{}\tDsrdtr:{}\tXonxoff:{}
| WriteTimeout:{}\tInterCharTimeout:{}
 -------------------------------------------
'''
def Init_Loger():
    logger.remove() 
    logger.add(
        sink=sys.stdout,
        format="<green>[{time:HH:mm:ss}]</green><level>[{level}]</level> -> <level>{message}</level>",
        level="INFO"
    )
class Main():
    def __init__(self,args):
        self.COM = args.COM
        self.Baud = args.Baud
        

    def Server(self):
        try:
            ser = serial.Serial(self.COM, self.Baud, timeout=0.5)
        except:
            logger.error("COM Interface exception")
            return False
        else:
            print(CONF.format(ser.name,ser.baudrate,ser.bytesize,ser.parity,ser.stopbits,ser.timeout,ser.writeTimeout,\
                ser.xonxoff,ser.rtscts,ser.dsrdtr,ser.interCharTimeout))
            logger.info("Equipment monitoring")
            print("========================")
            while True:
                try:
                    Metthod = input("PAYLAOD:")
                    
                    if '1' in Metthod:
                        ser.write('1'.encode("utf-8"))
                    elif '2' in Metthod:
                        ser.write('2'.encode("utf-8"))
                    else:
                        logger.error("No Payload")
                    logger.info("Command Run Success")
                except Exception as e:
                    print(e)
                    print("[-]Exit")
                    break
                                   

    def run(self):
        if self.COM:
            self.Server()
        else:
            logger.error("You must select a valid COM port!")

def main():
    print(Logo, "\n")
    Init_Loger()
    parser = argparse.ArgumentParser(
        formatter_class=argparse.RawTextHelpFormatter,
        epilog=textwrap.dedent('''
        Example:
            author-Github==>https://github.com/MartinxMax
        Basic usage:
            python3 {C51} -c (COM13) -b (Baud rate) #You must fill in the COM port, and then you can not fill in the baud rate, which is 9600 by default
            '''.format(C51=sys.argv[0]
                       )))
    parser.add_argument('-c', '--COM', default=None, help='COM Port')
    parser.add_argument('-b', '--Baud', type=int, default=9600, help='Baud rate')
    Main(parser.parse_args()).run()


if __name__ == '__main__':
    main()
