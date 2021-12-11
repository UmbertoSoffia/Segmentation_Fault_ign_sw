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
  date: {
	type: Date,
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

mongoose.pluralize(null)
const model = mongoose.model('Match', matchSchema)

module.exports = model