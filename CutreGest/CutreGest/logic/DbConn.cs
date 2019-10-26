using System;
using System.Data;
using MySql.Data.MySqlClient;

namespace CutreGest.logic {
    public class DbConn {

        private static DbConn instance;

        private DbConn() {
            Connection = new MySqlConnection("server=localhost;database=dbGest;user=root;password=sistemas;ssl-mode=none");
            Connection.Open();
        }

        public static DbConn Instance { get {
                if (instance == null)
                    instance = new DbConn();
                return instance;
            }
        }

        public IDbConnection Connection { get; }


    }
}
