using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net;
using System.IO;
using System.Net.Sockets;
using System.Diagnostics;
using System.Security.Cryptography;

namespace HackUniversity_server
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }
        string myIP = System.Net.Dns.GetHostByName(System.Net.Dns.GetHostName()).AddressList[0].ToString();
        string portin = "8005";
        string[] key = { null,null,null,"key9111785035" };
        bool need_to_send = false;
        bool nda = false;
        string savedsmile = "";
        private void Form1_Load(object sender, EventArgs e)

        {
            textBox3.Text = myIP;
            textBox1.Text = "192.168.1.65:8000";
           

        }
        public string memes()
        {
            string mem;
            string[] emo = { "U+1F60D", "U+1F929", "U+1F61C", "U+1F61D", "U+1F911", "U+1F910", "U+1F634", "U+1F922", "U+1F60E", "U+1F607" };
            Random rnd = new Random(DateTime.Now.Millisecond);
            int[] rndeq= {-1,-1,-1,-1, -1 };

            for (int q = 0; q < rndeq.Length; q++)
            {
                rndeq[q] = rnd.Next(0, 9);
                for (int i = 0; i < q; i++)
                {
                    if (rndeq[q] == rndeq[i])
                    {
                        q--;
                        break;
                    }


                }
            }
           
            mem = emo[rndeq[0]] + ';' + emo[rndeq[1]] + ';' + emo[rndeq[2]] + ';' + emo[rndeq[3]] + ';' + emo[rndeq[4]];
            savedsmile = mem;
            return mem;
        }
        public int check_cookie(string identify)
        {
            if(identify.Contains("key"))
            {
                foreach(string a in key)
                {
                    if (a == identify)
                        return 0;//известный пользователь

                }
               
                return 5;
            }
            else if (identify.Contains("App"))
            {
                return 1;//приложение
            }
            else  if (identify.Contains("NeedToAuth"))
            {
                return 4;//запрос на проверку смайлов
            }
            else
            return 2;//номер телефона,отправляем эмодзи
        }
        private void button2_Click(object sender, EventArgs e)
        {
            int port = 8005; // порт для приема входящих запросов
            IPAddress Addr = IPAddress.Parse(myIP);
            
                TcpListener server = new TcpListener(Addr, port);
                server.Start();
                int bytes=0;
                string response="000";

                while (true)
                {
                try
                {
                    Console.WriteLine("Ожидание подключений... ");
                    TcpClient client = server.AcceptTcpClient();
                    client.ReceiveTimeout = 2000;
                    client.SendTimeout = 5000;
                    Console.WriteLine("Подключен клиент. Выполнение запроса...");
                    NetworkStream stream = client.GetStream();
                    byte[] data = new byte[9000];
                    bytes = stream.Read(data, 0, data.Length); // получаем количество считанных байтов
                    string message = Encoding.UTF8.GetString(data, 0, bytes);
                    Console.WriteLine("Сообщение: {0}", message);
                    int stat = check_cookie(message);
                    if (stat == 0)
                    {   //известный пользователь, отвечаем сайту что можно авторизовать
                        response ="Approved"; 
                    }
                    else if (stat == 2)
                    {
                        need_to_send = true;
                        nda = true;
                        //неизвестный пользователь,флаг на отправку эмодзи эмодзи
                    }
                    else if((stat == 1) && need_to_send == false)
                    {
                        response = "000";//запрос от приложения, но без отправления
                    }
                    else if ((stat == 1) && need_to_send==true)
                          {
                            response = memes();//запрос от приложения, отправляю смайлы
                        need_to_send = false;
                          }
                    else if (stat == 4 && nda ==true)
                    {
                        string temp = savedsmile.Substring(savedsmile.Length - 7);
                        if( message.Contains( temp))//произвожу проверку смайлов с сайта
                        { response = "aph"; }
                        else { response = "000"; }
                    }

                    Console.WriteLine("Отправлено сообщение: {0}", response);
                    data = Encoding.UTF8.GetBytes(response);
                    stream.Write(data, 0, data.Length);
                    stream.Close();
                    client.Close();

                }
                catch (Exception)
                { Console.WriteLine("TIME OUT ");}


           
                }
            
        }

    }


          
    
}
//try
//{
//    HttpWebRequest request = (HttpWebRequest)WebRequest.Create("http://" + textBox1.Text);
//    request.Method = "POST";
//    string data = "sVanya pidor";
//    byte[] byteArray = System.Text.Encoding.UTF8.GetBytes(data);
//    request.ContentType = "application/x-www-form-urlencoded";
//    request.ContentLength = byteArray.Length;
//    request.ContentType = "application/x-www-form-urlencoded";
//    using (Stream dataStream = request.GetRequestStream())
//    {
//        dataStream.Write(byteArray, 0, byteArray.Length);
//    }

//    HttpWebResponse response = (HttpWebResponse)request.GetResponse();
//    WebHeaderCollection headers = response.Headers;
//    response.Close();
//    richTextBox1.Text = headers.ToString();
//}
//catch (Exception ex)
//{ richTextBox1.Text = ex.ToString(); }