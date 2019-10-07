using System.Data;

namespace Serpis.ad {
    public class DbCommandHelper {

        public static void AddParameter(IDbCommand dbCommand, string name, object value) {
            IDbDataParameter dataParameter = dbCommand.CreateParameter();
            dataParameter.ParameterName = name;
            dataParameter.Value = value;
            dbCommand.Parameters.Add(dataParameter);
        }
    }
}
