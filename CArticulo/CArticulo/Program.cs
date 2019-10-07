using System;
using System.Data;
using MySql.Data.MySqlClient;
using CSerpisAd;


namespace CArticulo {
    class MainClass {

        private static IDbConnection dbConn = new MySqlConnection("server=localhost;database=dbpruebas;user=root;password=sistemas;ssl-mode=none");

        public static void Main(string[] args) {
            dbConn.Open();
            Menu.Create("Menú principal:").Add("1 - Añadir artículos", NewArticle).Add("2 - Listar articulos", GetArticles)
                .ExitWhen("0 - Salir").Loop();
                
        }

        private static void NewArticle() {
            
        }

        private static void GetArticles() {
            IDbCommand dbCommand = dbConn.CreateCommand();
            dbCommand.CommandText = "select a.id, a.nombre, c.nombre as categoria, a.precio from articulo a, categoria c where a.categoria = c.id";
            IDataReader dataReader = dbCommand.ExecuteReader();
            Console.WriteLine("\n{0,-5} - {1,-25} - {2,-15} - {3,-5}\n",dataReader.GetName(0), dataReader.GetName(1),
                dataReader.GetName(2), dataReader.GetName(3));
            while (dataReader.Read()) {
                Console.WriteLine("{0,-5} - {1,-25} - {2,-15} - {3,-5}\n", dataReader.GetInt32(0), dataReader.GetString(1),
                    dataReader.GetString(2), dataReader.GetFloat(3));
            }
        }

    }
}
