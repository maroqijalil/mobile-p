package com.example.transactionapp.model

abstract class Model {
  abstract fun getTableName(): String
  abstract fun toMap(): Map<String, Any>
  abstract fun tableAttr(): String
}
