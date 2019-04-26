import React, { Component } from 'react'
import './relatory.css';

interface Props {
  relatory: any;
}

interface State {
  showRelatories: boolean
}

export default class Relatory extends Component<Props, State> {

  constructor(props: Props) {
    super(props)
    this.state = {
      showRelatories: false
    }
  }

  render() {
    const relatory = this.props.relatory;
    return (
      <React.Fragment>

        <div className='border'>
          <div>
            <h6>Company With Highest Capital</h6>
            {relatory.companiesWithLowestCapital.map((c: any, i: any) => {
              return (
                <div key={i}>
                  <p>Id: {c.id}</p>
                  <p>{c.type}</p>
                  <p>Name: {c.name}</p>
                  <p>Capital: € {relatory.companyHighestCapital}</p>
                  <p>{c.numberOfShares}</p>
                  <p>Sold Shares: {c.numberOfShares - c.availableShares}</p>
                  <p>{c.sharePrice}</p>
                  <p>{c.createdAt}</p>
                  <p>{c.updatedAt}</p>
                </div>

              );
            })}
          </div>
        </div>

      </React.Fragment>
    )
  }

}
