const mongoose = require('mongoose')

const structureSchema = new mongoose.Schema({
  structure_id: {
    type: String,
    unique: true,
    required: true
  },
  name: {
	type: String,
	unique: true,
	required: true
  },
  address_id: {
    type: String,
    required: true
  },
  number: {
    type: Number,
    required: true
  },
  description: String,
  start_time: String,
  stop_time: String,
  working_days: String,
  promoter_id: {
    type: String,
    unique: true,
    required: true
  }
}, { timestamps: true })

structureSchema.virtual('address', {
  ref: 'Address',
  localField: 'address_id',
  foreignField: 'address_id',
  justOne: true
})

structureSchema.virtual('promoter', {
  ref: 'Promoter',
  localField: 'promoter_id',
  foreignField: 'promoter_id',
  justOne: true
})

structureSchema.index({ structure_id: 1, promoter_id: 1 })

mongoose.pluralize(null)
const model = mongoose.model('Structure', structureSchema)

module.exports = model