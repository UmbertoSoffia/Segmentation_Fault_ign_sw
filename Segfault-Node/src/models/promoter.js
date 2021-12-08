const mongoose = require('mongoose')
const bcrypt = require('bcrypt')

const promoterSchema = new mongoose.Schema({
  promoter_id: {
    type: String,
    unique: true,
    required: true
  },
  token: String,
  email: {
    type: String,
    unique: true,
    required: true,
    match: /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
  },
  password: {
    type: String,
    minLength: 8
  },
  last_login: Date,
  language: {
    type: String,
    required: true
  },
  name: {
      type: String,
      required: true
    },
  address_id: {
      type: String,
      required: true
    }
}, { timestamps: true })

promoterSchema.virtual('address', {
  ref: 'Address',
  localField: 'address_id',
  foreignField: 'address_id',
  justOne: true
})

promoterSchema.pre('save', function (next) {
  const prom = this
  if (!prom.isModified('password')) return next()
  bcrypt.genSalt(10, (err, salt) => {
    if (err) return next(err)
    bcrypt.hash(prom.password, salt, (err, hash) => {
      if (err) return next(err)
      prom.password = hash
      next()
    })
  })
})

promoterSchema.methods.comparePassword = function (candidatePassword) {
  return bcrypt.compare(candidatePassword, this.password)
}

promoterSchema.index({ email: 1, password: 1 })

mongoose.pluralize(null)
const model = mongoose.model('Promoter', promoterSchema)

module.exports = model