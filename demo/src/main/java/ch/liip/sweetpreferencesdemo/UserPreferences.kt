package ch.liip.sweetpreferencesdemo

import ch.liip.sweetpreferences.SweetPreferences
import ch.liip.sweetpreferences.delegate


class UserPreferences constructor(sweetPreferences: SweetPreferences) {
    var counter: Int by sweetPreferences.delegate(0)
}
