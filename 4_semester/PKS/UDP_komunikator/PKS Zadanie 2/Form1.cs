using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PKS_Zadanie_2
{
    public partial class Form1 : Form
    {
        Program p = new Program(); 
        public Form1()
        {
            InitializeComponent();
            button1.Hide();
            button2.Hide();
            button3.Hide();
            button4.Hide();
            label1.Hide();
            label4.Hide();
            textBox1.Hide();
            textBox3.Hide();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            richTextBox1.AppendText(p.posli(textBox1.Text, textBox3.Text));
        }

        private void button5_Click(object sender, EventArgs e)
        {
            richTextBox1.AppendText(p.potvrdPort(textBox4.Text, textBox2.Text));
            button1.Show();
            button2.Show();

        }


        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            Environment.Exit(0);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            button1.Show();
            button2.Hide();
            button3.Hide();
            button4.Show();
            label1.Hide();
            label4.Hide();
            textBox1.Hide();
            textBox3.Hide();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            richTextBox1.AppendText(p.pocuvaj());
        }

        private void button1_Click(object sender, EventArgs e)
        {
            button1.Hide();
            button2.Show();
            button3.Show();
            button4.Hide();
            label1.Show();
            label4.Show();
            textBox1.Show();
            textBox3.Show();
        }

        private void button6_Click(object sender, EventArgs e)
        {
            richTextBox1.Text = "";
        }
    }
}
