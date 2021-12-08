const mongoose = require('mongoose')

const reservationSchema = new mongoose.Schema({
  reservation_id: {
    type: String,
    unique: true,
    required: true
  }
}, { timestamps: true })

mongoose.pluralize(null)
const model = mongoose.model('Reservation', reservationSchema)

module.exports = model