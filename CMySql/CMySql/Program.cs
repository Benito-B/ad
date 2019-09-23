using System;
using MySql.Data.MySqlClient;

namespace CMySql
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Acceso a dbprueba con MySql");
            MySqlConnection conn = new MySqlConnection("server=localhost;database=dbpruebas;user=root;password=sistemas;ssl-mode=none");
            conn.Open();
        }
    }
}
