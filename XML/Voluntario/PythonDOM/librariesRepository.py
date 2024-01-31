import mysql.connector
def create_connection():
    conn = None
    try:
        conn = mysql.connector.connect(
            host="localhost",
            port=3306,
            user="root",
            password="infobbdd",
            database="pruebaPython"
        )
        return conn
    except conn.connector.Error as e:
        print(e)

    return conn

def dopTables(conn):
    try:
        cursor = conn.cursor()
        cursor.execute("DROP TABLE IF EXISTS books")
        cursor.execute("DROP TABLE IF EXISTS libraries")
        conn.commit()
    except conn.connector.Error as e:
        print(e)

def crateTablelibraries(conn):
    try:
        cursor = conn.cursor()
        cursor.execute("""
            CREATE TABLE libraries (
                id INT AUTO_INCREMENT PRIMARY KEY,
                address VARCHAR(255) NOT NULL)
            """)
        conn.commit()
    except conn.connector.Error as e:
        print(e)
def crateTableBooks(conn):
    try:
        cursor = conn.cursor()
        cursor.execute("""
            CREATE TABLE books (
                id VARCHAR(255) PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                author VARCHAR(255),
                genre VARCHAR(255),
                price DECIMAL(10, 2),
                publish_date VARCHAR(255),
                description VARCHAR(255),
                library_id INT,
                FOREIGN KEY (library_id) REFERENCES libraries(id)
            )
            """)
        conn.commit()
    except conn.connector.Error as e:
        print(e)
        
def insertLibrary(conn, library):
    try:
        cursor = conn.cursor()
        id = library.getId()
        address = library.getAddress()
        cursor.execute("INSERT INTO libraries (id, address) VALUES (%s, %s)", (id, address))
        conn.commit()
    except conn.connector.Error as e:
        print(e)

def insertBook(conn, book, library_id):
    try:
        cursor = conn.cursor()
        id = book.getId()
        title = book.geTitle()
        author = book.getAuthor()
        genre = book.getGenre()
        price = book.getPrice()
        publish_date = book.getPublish_date()
        description = book.getDescription()
        
        cursor.execute("""
                INSERT INTO books (id, title, author, genre, price, publish_date, description, library_id)
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
                """, (id, title, author, genre, price, publish_date, description, library_id))
        conn.commit()
    except conn.connector.Error as e:
        print(e)