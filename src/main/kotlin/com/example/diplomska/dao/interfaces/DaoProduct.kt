package com.example.diplomska.dao.interfaces

import com.example.diplomska.model.Category
import com.example.diplomska.model.Product

interface DaoProduct : DaoCrud<Product>{
   fun getByBarcode(barcode: Int) : Product?
   fun getInStock() : List<Product>
   fun getOutOfStock() : List<Product>
   fun getByCategory(category: Category): List<Product>
   fun getActiveProducts() : List<Product>
   fun getInActiveProducts(): List<Product>
}