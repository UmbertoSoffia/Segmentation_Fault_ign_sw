const mongoose = require('mongoose')

const structureSchema = new mongoose.Schema({
  structure_id: {
    type: String,
    required: true,
	unique: true
  },
  name: {
	type: String,
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
  foreignField: 'promoter_id'
})

structureSchema.index({name: 1, promoter_id: 1 }, {unique: true})

mongoose.pluralize(null)
const model = mongoose.model('Structure', structureSchema)

module.exports = model