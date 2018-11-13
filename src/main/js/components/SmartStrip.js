import React, {Component} from "react";
import SmartPlug from "./SmartPlug";
import {Row} from 'reactstrap';

class SmartStrip extends Component {

    constructor(props) {
        super(props);
        this.state = {stripstatus: {}};
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
        const plugs = this.transposeStripResponse();
        return (
            <span>{strip.name}
                <Row>
                    {plugs.map((plug) => {
                        return <SmartPlug plug = {plug}/>
                    })}
                </Row>
            </span>
        );
    }

    transposeStripResponse() {
        const plugs = [];
        if (this.state.stripstatus.switch) {
            this.state.stripstatus.switch.map((plug, index) => {
                plugs.push({
                    index: index,
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