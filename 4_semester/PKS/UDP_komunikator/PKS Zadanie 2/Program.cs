using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Collections;
using System.Net.NetworkInformation;

namespace PKS_Zadanie_2
{
    class Program
    {
        IPAddress ipCiel = IPAddress.Parse("192.168.1.105"); //default
        int port;

        public String potvrdPort(String vstupPort, String ipciel)
        {
            Int32.TryParse(vstupPort, out port);
            if (port == 0) port = 2505; //default ak zostane nevyplnené
            if (ipciel.Equals("") == false) ipCiel = IPAddress.Parse(ipciel);
            return "Port a IP boli nastavené\n";
        }
        
        public String posli(String sprava, String maxVelkost)
        {
            int maximum;
            Int32.TryParse(maxVelkost, out maximum);
            if (maximum == 0) maximum = 100; //default ak zostane nevyplnené

            if (maximum < 13) maximum = 13; //12 je hlavička, zmení minimalne 13 (1byte na dáta)
            
            UdpClient udp = new UdpClient();
            IPEndPoint endPoint = new IPEndPoint(ipCiel, port);
            Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Dgram,ProtocolType.Udp);

            Ping ping = new Ping(); //aby sa nestratila 1. správa
            PingReply pingReply = ping.Send(ipCiel);

            int dlzka = sprava.Length;
            int i;
            Byte[] data = Encoding.ASCII.GetBytes(sprava);
            Byte[] protokol;
            Byte[] dlzkaDat;
            Byte[] cisloFragmentu;
            int pocet = 1;
            

            if (maximum < dlzka + 12)
            {
                Byte[] fragment = new Byte[maximum];
                int zvysok = dlzka % (maximum - 12); //zvyšok
                pocet = dlzka / (maximum - 12); //pocet fragmentov
                int hranica = 0;
                int dlzkaDatInt;
                if (zvysok > 0) pocet++;

                for (i = 0; i < pocet; i++)
                {
                    protokol = new Byte[4];
                    protokol = BitConverter.GetBytes(pocet);
                    dlzkaDat = new Byte[4];
                    cisloFragmentu = new Byte[4];
                    if (i == pocet - 1 && zvysok > 0) //posledny fragment je už len zvyšok
                    {
                        dlzkaDat = BitConverter.GetBytes(zvysok);
                        dlzkaDatInt = zvysok;
                    }
                    else
                    {
                        dlzkaDat = BitConverter.GetBytes(maximum - 12);
                        dlzkaDatInt = maximum - 12;
                    }

                    cisloFragmentu = BitConverter.GetBytes(i);
                    Buffer.BlockCopy(protokol, 0, fragment, 0, 4);
                    Buffer.BlockCopy(dlzkaDat, 0, fragment, 4, 4);
                    Buffer.BlockCopy(cisloFragmentu, 0, fragment, 8, 4);
                    Buffer.BlockCopy(data, 0 + hranica, fragment, 12, dlzkaDatInt);
                    hranica += maximum - 12;

                    udp.Send(fragment, fragment.Length, endPoint);
                }
            }
            else //fragment je len 1, zmestí sa
            {
                Byte[] complet = new Byte[maximum];
                protokol = new Byte[4];
                dlzkaDat = new Byte[4];
                cisloFragmentu = new Byte[4];
                protokol = BitConverter.GetBytes(1);
                dlzkaDat = BitConverter.GetBytes(dlzka);
                cisloFragmentu = BitConverter.GetBytes(0);

                Buffer.BlockCopy(protokol, 0, complet, 0, 4);
                Buffer.BlockCopy(dlzkaDat, 0, complet, 4, 4);
                Buffer.BlockCopy(cisloFragmentu, 0, complet, 8, 4);
                Buffer.BlockCopy(data, 0, complet, 12, dlzka);

                udp.Send(complet, complet.Length, endPoint);
            }
            socket.Close();
            udp.Close();
            return "Správa: " + Encoding.ASCII.GetString(data) + "\nbola odoslaná a má " + pocet + " fragmentov\n"; 
        }


        public String pocuvaj()
        {
            UdpClient listener = new UdpClient(port);
            IPEndPoint endPoint = new IPEndPoint(ipCiel, port);
            Byte[] bytes = null;
            Byte[] dlzka;
            int dlzkaInt;
            Byte[] sprava;
            int cislo;
            Byte[] pomPoradie;

            String[] spravy = null; //pole s fragmentami
            StringBuilder spoj = new StringBuilder(); //výpis

            int pocetFragmentov = 0;
            int aktPocet = 0;

            while (true) //počúvam
            {
                dlzka = new Byte[4];
                pomPoradie = new Byte[4];
                Byte[] protokol = new Byte[4];
                int cisloProtokolu = 0;
                bytes = listener.Receive(ref endPoint);
                Buffer.BlockCopy(bytes, 0, protokol, 0, 4);
                cisloProtokolu = BitConverter.ToInt32(protokol, 0); //1. integer v hlavičke
                if (aktPocet == 0) //1. prijatá správa
                {
                    pocetFragmentov = cisloProtokolu; //nastavím hranicu
                    spravy = new String[pocetFragmentov];
                }
                if (cisloProtokolu == pocetFragmentov) //nejaka slabšia kontrola, či majú rovnaký počet fragmentov
                {
                    Buffer.BlockCopy(bytes, 8, pomPoradie, 0, 4);
                    cislo = BitConverter.ToInt32(pomPoradie, 0);

                    Buffer.BlockCopy(bytes, 4, dlzka, 0, 4);
                    dlzkaInt = BitConverter.ToInt32(dlzka, 0);
                    sprava = new Byte[dlzkaInt];
                    Buffer.BlockCopy(bytes, 12, sprava, 0, dlzkaInt);

                    spravy[cislo] = Encoding.ASCII.GetString(sprava); //pridanie správy na správny index
                    aktPocet++;
                }

                if (aktPocet == pocetFragmentov) break; //ked doprijímal všetky fragmenty
            }

                    spoj.Clear();
                    foreach (String b in spravy) //skladanie stringu
                    {
                        spoj.Append(b);
                    }
                    listener.Close();
                    return "Správa od: " + endPoint.ToString() + "\n" + spoj.ToString() + "\n" +
                        "Táto správa mala " + pocetFragmentov + " fragmentov.\n";                    
        }


        [STAThread]
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
    }
}
