const mongoose = require('mongoose')

const reservationSchema = new mongoose.Schema({
  reservation_id: {
    type: String,
    unique: true,
    required: true
  },
  match_id: {
    type: String,
    required: true
  },
  user_id: {
    type: String,
    required: true
  }
}, { timestamps: true })

reservationSchema.index({match_id: 1, user_id: 1 }, {unique: true})

reservationSchema.virtual('match', {
  ref: 'Match',
  localField: 'match_id',
  foreignField: 'match_id',
  justOne: true
})

reservationSchema.virtual('user', {
  ref: 'User',
  localField: 'user_id',
  foreignField: 'user_id',
  justOne: true
})

mongoose.pluralize(null)
const model = mongoose.model('Reservation', reservationSchema)

module.exports = model