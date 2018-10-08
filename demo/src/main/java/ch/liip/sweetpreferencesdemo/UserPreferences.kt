package ch.liip.sweetpreferencesdemo

import ch.liip.sweetpreferences.SweetPreferences

class UserPreferences constructor(sweetPreferences: SweetPreferences) {
    var counter: Int by sweetPreferences.delegate(0)
}
