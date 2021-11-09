import { Switch, Route, Redirect } from 'react-router-dom';
import Landing from 'pages/Landing';
import Profile from 'pages/Profile';
import Login from 'pages/Login';
import Register from 'pages/Register';
import Match from 'pages/match/Match';
import Reservation from 'pages/Reservation/Reservation'
// Font Awesome Style Sheet
import '@fortawesome/fontawesome-free/css/all.min.css';

// Tailwind CSS Style Sheet
import 'assets/styles/tailwind.css';

function App() {
    return (
        <Switch>
            <Route exact path="/" component={Landing} />
            <Route exact path="/profile" component={Profile} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/register" component={Register} />
            <Route exam path='/match' component={Match} />
            <Route exact path='/reservation' component={Reservation}/>
            <Redirect from="*" to="/" />
        </Switch>
    );
}

export default App;
