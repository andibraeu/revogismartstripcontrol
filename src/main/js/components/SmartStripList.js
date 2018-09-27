import React, {Component} from "react";
import SmartStrip from "./SmartStrip";

class SmartStripList extends Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        fetch("/discover")
            .then(response => response.json())
            .then((data) => {
                let state = {};
                state.strips = data;
                this.setState(state);
            });
    }

    render() {
        const list = [];
        for (let stripsKey in this.state.strips) {
            let strip = this.state.strips[stripsKey];
            list.push(
                 <SmartStrip
                     strip={strip}
                 />)
        }
        return (
            <div className="smartstriplist">
                {list}
            </div>
        );
    }
}

export default SmartStripList;