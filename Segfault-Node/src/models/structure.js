const mongoose = require('mongoose')

const structureSchema = new mongoose.Schema({
  structure_id: {
    type: String,
    unique: true,
    required: true
  }
}, { timestamps: true })

mongoose.pluralize(null)
const model = mongoose.model('Structure', structureSchema)

module.exports = model