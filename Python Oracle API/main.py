import oracledb
from oracledb import Connection, Cursor


class OracleConnector:
    user: str
    password: str
    dsn: str

    conn: Connection
    cursor: Cursor

    def __init__(self, user, password, dsn):
        self.user = user
        self.password = password
        self.dsn = dsn

    def connect(self):
        """
        Oracle DB와 연결을 수행한다.
        """
        self.conn = oracledb.connect(user=self.user, password=self.password, dsn=self.dsn)
        self.cursor = self.conn.cursor()


if __name__ == "__main__":
    oracle_connector = OracleConnector(
        user="c##youngwoo",
        password="password",
        dsn="192.168.53.9:1521/ORCLCDB"
    )
    oracle_connector.connect()
    print("[INFO] Oracle DB와 연결되었습니다.")
    