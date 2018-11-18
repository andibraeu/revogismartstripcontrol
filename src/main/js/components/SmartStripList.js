import React, {Component} from "react";
import SmartStrip from "./SmartStrip";
import {Col, Row} from 'reactstrap';

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
        if (this.state.strips.length > 0) {
            return (
                <Row>
                    {this.state.strips.map((strip) => {
                        return <SmartStrip strip={strip}/>
                    })}

                </Row>
            );
        } else {
            return <Row><Col>Could not find any smart strips :-(</Col></Row>
        }
    }
}

export default SmartStripList;