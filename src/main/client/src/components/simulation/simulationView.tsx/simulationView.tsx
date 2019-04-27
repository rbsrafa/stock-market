import React, { Component } from 'react';
import './simulationView.css';
import Relatory from '../../relatories/relatory/relatory';

interface Props {
  simulation: any;
}

interface State {
  showRelatories: boolean
}

export default class SimulationView extends Component<Props, State> {

  constructor(props: Props) {
    super(props)
    this.state = {
      showRelatories: false
    }
  }

  render() {
    const className = ` btn btn-lg btn${this.state.showRelatories ? '' : '-outline'}-dark w-100 hover`;
    const simulation = this.props.simulation;

    return (
      <div className='mb-2'>
        <div
          onClick={() => this.setState({ showRelatories: !this.state.showRelatories })}
          className={className}
        >
          <h5 className='sim-margin'>Simulation {simulation.simulationId}</h5>
          <span className='small'>Number of Transactions: {simulation.numberOfTransactions}</span>
        </div>

        {this.state.showRelatories ?
          (
            <Relatory relatory={this.props.simulation} />
          ) : <div></div>}

      </div>
    )

  }
}
