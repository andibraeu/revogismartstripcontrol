import React, {Component} from "react";

class SmartStrip extends Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        fetch("/status/" + this.props.strip.sn)
            .then(response => response.json())
            .then((data) => {
                let state = {};
                state.stripstatus = data;
                this.setState(state);
            });
    }

    render() {
        const strip = this.props.strip;
        return (
            <div className="smartstrip">
                <span>{strip.name}</span>
            </div>
        );
    }
}

export default SmartStrip;