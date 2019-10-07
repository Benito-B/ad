using System;
using System.Data;
using MySql.Data.MySqlClient;

namespace CMySql
{
    class MainClass {
        private static IDbConnection dbConn = new MySqlConnection("server=localhost;database=dbpruebas;user=root;password=sistemas;ssl-mode=none");

        public static void Main(string[] args) {
            Console.WriteLine("Acceso a dbprueba con MySql");
            dbConn.Open();

            /*Console.Write("Introduce nombre: ");
            Insert(Console.ReadLine());*/
            ShowAll();
            dbConn.Close();
        }

        public static void ShowAll() {
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = "select * from categoria order by id";
            IDataReader dataReader = dbCommand.ExecuteReader();
            Console.WriteLine("{0,-3} -- {1,-15}", dataReader.GetName(0), dataReader.GetName(1));
            while (dataReader.Read())
                Console.WriteLine("{0,-3} -- {1,-15}", dataReader[0], dataReader[1]);
            dataReader.Close();
        }

        public static void Insert(string dato) {
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = string.Format("insert into categoria(nombre) values ('{0}')", dato);
            dbCommand.ExecuteNonQuery();
        }
    }
}
