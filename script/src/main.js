import "@riotjs/hot-reload"
import App from './app.riot'
import {component} from 'riot'
import 'semantic-ui-css/semantic.min.css'

component(App)(document.getElementById('app'))