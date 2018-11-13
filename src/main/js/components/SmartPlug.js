import React, {Component} from "react";
import {Col} from 'reactstrap';

class SmartPlug extends Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        const plug = this.props.plug;
        console.log(this.props);
        return (
            <Col sm="4">
                <div>No. {plug.index}</div>
                <div>Status {plug.switch}</div>
                <div>Watt {plug.watt}</div>
                <div>Amp {plug.amp}</div>
            </Col>
        );
    }

}

export default SmartPlug;