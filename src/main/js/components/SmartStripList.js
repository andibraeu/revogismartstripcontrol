import React, {Component} from "react";
import SmartStrip from "./SmartStrip";
import {Row} from 'reactstrap';

class SmartStripList extends Component {

    constructor(props) {
        super(props);
        this.state = {strips: []};
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
        return (
            <Row>
                {this.state.strips.map((strip) => {
                    return <SmartStrip strip = {strip}/>
                })}
            </Row>
        );
    }
}

export default SmartStripList;