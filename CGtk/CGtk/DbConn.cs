using System;
using System.Data;
using MySql.Data.MySqlClient;

namespace CGtk {
    public class DbConn {
        private static DbConn instance;

        private IDbConnection mysqlConn;

        private DbConn() {
            mysqlConn = new MySqlConnection("server=localhost;database=dbpruebas;user=root;password=sistemas;ssl-mode=none");
            mysqlConn.Open();
        }

        public static DbConn GetInstance() {
            if (instance == null)
                instance = new DbConn();
            return instance;
        }

        public IDbConnection GetConnection() {
            return mysqlConn;
        }
    }
}
