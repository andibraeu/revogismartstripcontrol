import React, {Component} from "react";
import {Col} from 'reactstrap';

class SmartPlug extends Component {

    constructor(props) {
        super(props);
        this.state = {};
        this.changeStatus = this.changeStatus.bind(this);
    }

    changeStatus(e) {
        this.props.changeStatus(this.props.plug.index, e.target.checked ? 1 : 0);
    }

    render() {
        const plug = this.props.plug;
        return (
            <Col sm="4">
                <div>No. {plug.index}</div>
                <div><label className="switch">
                    <input checked={plug.switch === 1} type="checkbox" onChange={this.changeStatus} />
                </label></div>
                <div>Status {plug.switch}</div>
                <div>Watt {plug.watt}</div>
                <div>Amp {plug.amp}</div>
            </Col>
        );
    }



}

export default SmartPlug;