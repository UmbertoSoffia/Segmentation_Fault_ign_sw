const mongoose = require('mongoose')

const matchSchema = new mongoose.Schema({
  match_id: {
    type: String,
    unique: true,
    required: true
  },
  name: {
	type: String,
	required: true
  },
  description: String,
  start_time: {
	type: String,
	required: true
  },
  stop_time: {
	type: String,
	required: true
  },
  structure_id: {
	type: String,
	required: true
  },
  creator_id: {
	type: String,
	required: true
  },
  creator_type: {
	type: String,
	required: true
  },
  date: {
	type: String,
	required: true
  },
  age_range: {
	type: String,
	required: true
  },
  number: {
	type: Number,
	required: true
  }
}, { timestamps: true })

matchSchema.virtual('structure', {
  ref: 'Structure',
  localField: 'structure_id',
  foreignField: 'structure_id',
  justOne: true
})

mongoose.pluralize(null)
const model = mongoose.model('Match', matchSchema)

module.exports = model