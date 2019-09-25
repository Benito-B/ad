using System;
using System.Data;
using MySql.Data.MySqlClient;

namespace CMySql
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Acceso a dbprueba con MySql");
            IDbConnection dbConn = new MySqlConnection("server=localhost;database=dbpruebas;user=root;password=sistemas;ssl-mode=none");
            dbConn.Open();

            IDbCommand dbCommand = dbConn.CreateCommand();

            dbCommand.CommandText = "select * from categoria";
            IDataReader dataReader = dbCommand.ExecuteReader();

            dbConn.Close();
        }
    }
}
