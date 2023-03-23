#include<reg51.h>
#include<intrins.h>
sbit PAYLOAD1=P2^0; 
sbit PAYLOAD2=P2^1; 
 
void Delay100ms()		//@11.0592MHz
{
	unsigned char i, j, k;

	_nop_();
	_nop_();
	i = 5;
	j = 52;
	k = 195;
	do
	{
		do
		{
			while (--k);
		} while (--j);
	} while (--i);
}

void UART_Init()
{
SCON = 0x50; 
PCON = 0x00; 
TMOD = 0x20;	
TH1 = 0xFd; 
TL1 = 0xFd; 
TR1 = 1;
ES = 1;	
EA = 1;			 
PAYLOAD1=1;
PAYLOAD2=1;
}

 
void UART_SendByte(unsigned char Byte){
	SBUF=Byte;
	//检测是否完成
	while(TI==0);
	TI=0;//TI复位
}

void main(){
	UART_Init();
	while(1);
}
void UART_Routine()    interrupt 4
{	 
	unsigned char DATA[1];
	if(RI==1){
		DATA[0]=SBUF;
	   	if(DATA[0]=='1'){
			PAYLOAD1=0;
			Delay100ms();
			 
			PAYLOAD1=1;
		}
		else if(DATA[0]=='2'){
			PAYLOAD2=0;
			Delay100ms();
		 
			PAYLOAD2=1;
		}
		RI=0;//复位
			UART_SendByte(DATA[0]);
	}
	
} 
