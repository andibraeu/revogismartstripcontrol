import React, {Component} from "react";
import SmartStripList from "./SmartStripList";
import {Container} from 'reactstrap';

class SmartStripApp extends Component {

    render() {
        return (
            <Container>
                <SmartStripList
                />
            </Container>
        );
    }
}

export default SmartStripApp;