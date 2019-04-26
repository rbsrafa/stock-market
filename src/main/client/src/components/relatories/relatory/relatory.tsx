import React, { Component } from 'react'
import './relatory.css';

interface Props {
  relatory: any;
}

interface State {
  showRelatories: boolean,
  showCWHC: boolean,
  showCWLC: boolean
}

export default class Relatory extends Component<Props, State> {

  constructor(props: Props) {
    super(props)
    this.state = {
      showRelatories: false,
      showCWHC: false,
      showCWLC: false
    }
  }

  componentDidMount() {
    console.log(this.props.relatory);
  }

  render() {
    return (
      <React.Fragment>

        <div className='border'>
          <div className='border'>
            {this._renderCompaniesWithHighestCapital()}
          </div>
          <div className='border'>
            {this._renderCompaniesWithLowestCapital()}
          </div>
        </div>

      </React.Fragment>
    )
  }

  private _renderCompaniesWithHighestCapital() {
    return (
      <div>
        <h6
          className='hover rel'
          onClick={() => this.setState({ showCWHC: !this.state.showCWHC })}
        >Companies With Highest Capital</h6>

        {this.state.showCWHC ? 
        (
          this.props.relatory.companiesWithHighestCapital.map((c: any, i: any) => {
            return (
              this._renderCompany(c, this.props.relatory.companyHighestCapital)
            );
          })
        ) : <div></div>}
      </div>
    )
  }

  private _renderCompaniesWithLowestCapital() {
    return (
      <div>
        <h6
          className='hover rel'
          onClick={() => this.setState({ showCWLC: !this.state.showCWLC })}
        >Companies With Lowest Capital</h6>

        {this.state.showCWLC ? 
        (
          this.props.relatory.companiesWithLowestCapital.map((c: any, i: any) => {
            return (
              this._renderCompany(c, this.props.relatory.companyLowestCapital)
            );
          })
        ) : <div></div>}
      </div>
    )
  }

  private _renderCompany(c: any, capital: any) {
    return (
      <div key={c.id}>
        <span><strong>Id:</strong> {c.id}</span><br />
        <span><strong>Company Type: </strong>{c.type}</span><br />
        <span><strong>Name:</strong> {c.name}</span><br />
        <span><strong>Capital:</strong> € {capital}</span><br />
        <span><strong>Number of Shares:</strong> {c.numberOfShares}</span><br />
        <span><strong>Sold Shares:</strong> {c.numberOfShares - c.availableShares}</span><br />
        <span><strong>Share Price: € </strong> {c.sharePrice}</span><br />
        <span><strong>Created at:</strong> {c.createdAt}</span><br />
        <span><strong>Updated at:</strong> {c.updatedAt}</span><br /><br />
      </div>
    );
  }

}
