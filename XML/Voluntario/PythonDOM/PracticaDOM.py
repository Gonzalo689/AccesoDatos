import xml.etree.ElementTree as ET
from Book import Book
from Library import Library
import librariesRepository as dataBase
import mysql.connector

'''
PRACTIVA VOLUNTARIA XML en python
En mi caso voy a mezclar un poco la practica DOM I y la practica DOM II
'''


# Función recursiva para recorrer el arbol XML y pasar los datos a las clases  
def recursion(root):
    global library
    global book
    if root.tag == "library" :
        library = Library(root.attrib["id"], "")
        librarys.append(library)
    if root.tag == "book":
        book = Book(root.attrib["id"], "", "", "", "", "", "")
        library.setBooks(book)
    if root.tag == "address":
        library.setAddress(root.text)

    data(root)

    for subelemento in root:
        recursion(subelemento)

def data(data):
    if data.text != "":
        if data.tag == "author":
            book.setAuthor(data.text)
        if data.tag == "title":
            book.setTitle(data.text)
        if data.tag == "genre":
            book.setGenre(data.text)
        if data.tag == "price":
            book.setPrice(data.text)
        if data.tag == "publish_date":
            book.setPublish_date(data.text)
        if data.tag == "description":
            book.setDescription(data.text)
            
# Promedio de precios de una libreria
def averagePrice(library):
    total = 0
    for book in library.books:
        total += float(book.getPrice())
    num_books = library.sizeBooks()
    if num_books > 0:
        return total / num_books
    else:
        return 0.0
# Conteo de generos de una libreria
def nameGenres(library):
    genres = []
    for i in range(library.sizeBooks()):  
        g = library.getBook(i).getGenre()
        if g != "" and g not in genres:
            genres.append(library.getBook(i).getGenre())
    return genres
# Conteo de libros por año
def booksYear(library):
    map = {}
    for i in range(library.sizeBooks()):  
        year = library.getBook(i).getPublish_date().split("-")[0]
        if year not in map:
            map[year] = 1
        else:
            map[year] += 1
    return map
            
# Nombre del archivo XML que deseas leer
archivo_xml = 'libraries.xml'

# Parsear el archivo XML
tree = ET.parse(archivo_xml)
root = tree.getroot()

librarys = []
library = None
book = None
recursion(root)

# Imprimir los datos de la actividad DOM I
for lib in librarys:
    print("Cantidad de libros: ", str(lib.sizeBooks()))
    print("Promedio de precio: ", str(averagePrice(lib)))
    print("Generos:")
    genres = nameGenres(lib)
    for g in genres:
        result = ', '.join(genres)
    print(result)   
    print("Libros por año:")
    result_map = booksYear(lib)
    for elem in result_map:
        print(f"{result_map[elem]} del año {elem}")
    
   
    print("------------------")



conn = dataBase.create_connection()
dataBase.dopTables(conn)
dataBase.crateTablelibraries(conn)
dataBase.crateTableBooks(conn)
for lib in librarys:
    dataBase.insertLibrary(conn, lib)
for libr in librarys: 
    for i in range(libr.sizeBooks()):
        libr.getBook(i)
        dataBase.insertBook(conn, libr.getBook(i), libr.getId())  
conn.close()