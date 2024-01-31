class Book:
    def __init__(self, id, title, author, genre, price, publish_date, description):
        self.id = id
        self.title = title
        self.author = author
        self.genre = genre
        self.price = price
        self.publish_date = publish_date
        self.description = description
    
    def getId(self):
        return self.id
    def setId(self, id):
        self.id = id
    def getAuthor(self):
        return self.author
    def setAuthor(self, author):
        self.author = author
    
    def geTitle(self):
        return self.title
    def setTitle(self, title):
        self.title = title
    def getGenre(self):
        return self.genre
    def getGenre(self):
        return self.genre
    def setGenre(self, genre):
        self.genre = genre
    def getPrice(self):
        return self.price
    def setPrice(self, price):
        self.price = price
    
    def getPublish_date(self):
        return self.publish_date
    def setPublish_date(self, publish_date):
        self.publish_date = publish_date
    
    def getDescription(self):
        return self.description
    
    def setDescription(self, description):
        self.description = description
    
    def __str__(self):
        return f"Id: {self.id}, Titulo: {self.title}, Autor: {self.author}, Genero: {self.genre}, Precio: {self.price}, Fecha de publicación: {self.publish_date}, Descripción: {self.description}"