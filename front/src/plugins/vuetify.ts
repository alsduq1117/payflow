import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { aliases, mdi } from 'vuetify/iconsets/mdi'
import '@mdi/font/css/materialdesignicons.css'


const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: 'mdi', // 👈 기본 아이콘 세트 지정
    aliases,
    sets: { mdi },
  },
})

export default vuetify
