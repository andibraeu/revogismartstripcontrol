import React, {Component} from "react";
import HelloWorld from "./components/HelloWorld";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            someValue: "21341234",
            otherValue: "123"
        }
    }

    componentWillMount() {
        fetch("/discover")
            .then(response => response.json())
            .then((data) => {
                this.setState(data)
            });
    }

    render() {
        return (
            <div className="App" align="right">
            <HelloWorld
        someValue={this.state.someValue}
        otherValue={this.state.otherValue}
        />
        </div>
    );
    }
}

export default App;