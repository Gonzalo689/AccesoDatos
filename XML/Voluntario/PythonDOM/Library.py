class Library:
    def __init__(self, id, address):
        self.id = id
        self.address = address
        self.books = []
    
    def getId(self):
        return self.id
    def setId(self, id):
        self.id = id
    
    def getAddress(self):
        return self.address
    
    def setAddress(self, address):
        self.address = address
    
    def setBooks(self, book):
        self.books.append(book)           
    
    def getBook(self, index):
        return self.books[index]   
    def sizeBooks(self):
        return len(self.books)
    
    def __str__(self):
        return f"Id: {self.id}, Dirección: {self.address}, Libros: {self.showBooks()}"
    
    def showBooks(self):
        return [str(book) for book in self.books]
