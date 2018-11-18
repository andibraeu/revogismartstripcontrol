import React, {Component} from "react";
import SmartPlug from "./SmartPlug";
import {Col, Row} from 'reactstrap';

class SmartStrip extends Component {

    constructor(props) {
        super(props);
        this.state = {stripstatus: {}};
        this.changeStatus = this.changeStatus.bind(this);
        this.changeOverallStatus = this.changeOverallStatus.bind(this);
    }

    changeOverallStatus(e) {
        this.changeStatus(0, e.target.checked ? 1 : 0)
    }

    changeStatus(index, targetState) {
        fetch("/switch/" + this.props.strip.sn + "/" + index + "?state=" + targetState, {
            method: 'PUT'
        })
            .then(response => response.json())
            .then((data) => {
                this.getStatus();
            })
    }

    getStatus() {
        fetch("/status/" + this.props.strip.sn)
            .then(response => response.json())
            .then((data) => {
                let state = {};
                state.stripstatus = data;
                state.active = true;
                this.setState(state);
            });
    }

    componentDidMount() {
        this.getStatus()
    }

    // return true if at least one plug is switched on
    getOverallSmartstripStatus() {
        if (this.state.stripstatus.switch) {
            if (this.state.stripstatus.switch.reduce((pv, cv) => pv+cv, 0) > 0) {
                return true;
            }
        }
        return false
    }

    render() {
        const strip = this.props.strip;
        const plugs = this.transposeStripResponse();
        return (
            <span>
                <Row>
                    <Col sm="6">
                        {strip.name} - {strip.sn}
                    </Col>
                    <Col sm="6">
                        <div>Overall switch <label className="switch">
                    <input checked={this.getOverallSmartstripStatus()} type="checkbox" onChange={this.changeOverallStatus} />
                </label></div>
                    </Col>
                </Row>
                <Row>
                    {plugs.map((plug) => {
                        return <SmartPlug plug = {plug}
                        changeStatus = {this.changeStatus}/>
                    })}
                </Row>
                </span>
        );
    }

    transposeStripResponse() {
        const plugs = [];
        if (this.state.stripstatus.switch) {
            this.state.stripstatus.switch.map((plug, index) => {
                let plugnumber = index + 1;
                plugs.push({
                    index: plugnumber,
                    switch: plug,
                    watt: this.state.stripstatus.watt[index],
                    amp: this.state.stripstatus.amp[index]
                })
            })
        }
        return plugs;
    }
}

export default SmartStrip;