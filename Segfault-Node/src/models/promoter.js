const mongoose = require('mongoose')

const promoterSchema = new mongoose.Schema({
  promoter_id: {
    type: String,
    unique: true,
    required: true
  }
}, { timestamps: true })

mongoose.pluralize(null)
const model = mongoose.model('Promoter', promoterSchema)

module.exports = model