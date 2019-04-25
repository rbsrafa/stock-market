import React, { Component } from 'react';
import './simulation.css';

interface Props {
  settings: Function;
}

interface State {
  numberOfInvestors: number | null;
  minInvestorBudget: number | null;
  maxInvestorBudget: number | null;
  numberOfCompanies: number | null;
  minShareNumber: number | null;
  maxShareNumber: number | null;
  minSharePrice: number | null;
  maxSharePrice: number | null;
}

export default class Simulation extends Component<Props, State> {

  constructor(props: Props) {
    super(props);
    this.state = {
      numberOfInvestors: null,
      minInvestorBudget: null,
      maxInvestorBudget: null,
      numberOfCompanies: null,
      minShareNumber: null,
      maxShareNumber: null,
      minSharePrice: null,
      maxSharePrice: null
    }

  }

  private _handleRunSimulation() {
    this.props.settings(this.state);
  }

  private _updateState(e: any) {
    let state = this.state;
    (state as any)[e.target.id] = e.target.value;
    this.setState(state);
    console.log(this.state);
  }

  render() {
    return (
      <div>
        <h5>Simulation Settings</h5>
        <form>
          <div className="form-group">
            <label htmlFor="numberOfInvestors">Number of Investors</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="numberOfInvestors"
              placeholder="" />
          </div>
          <div className="form-group">
            <label htmlFor="minInvestorBudget">Min Investor Budget</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="minInvestorBudget"
              placeholder="" />
          </div>
          <div className="form-group">
            <label htmlFor="maxInvestorBudget">Max Investor Budget</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="maxInvestorBudget"
              placeholder="" />
          </div>
          <div className="form-group">
            <label htmlFor="numberOfCompanies">Number of Companies</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="numberOfCompanies"
              placeholder="" />
          </div>
          <div className="form-group">
            <label htmlFor="minShareNumber">Min Number of Shares</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="minShareNumber"
              placeholder="" />
          </div>
          <div className="form-group">
            <label htmlFor="maxShareNumber">Max Number of Shares</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="maxShareNumber"
              placeholder="" />
          </div>
          <div className="form-group">
            <label htmlFor="minSharePrice">Min Share Price</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="minSharePrice"
              placeholder="" />
          </div>
          <div className="form-group">
            <label htmlFor="maxSharePrice">Max Share Price</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="maxSharePrice"
              placeholder="" />
          </div>
          <label onClick={() => this.props.settings(this.state)} className="btn btn-sm btn-primary">Run</label>
        </form>
      </div>
    )
  }
}
