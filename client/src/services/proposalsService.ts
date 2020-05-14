import * as request from 'request-promise';
import domain from './constants';
import {Proposal} from './serviceTypes';
import {Review} from './serviceTypes';





var proposalsService = {

    /* add proposal
      /api/conferences/{conferenceid}/proposals
      POST
      body:
        {
          "author": "email",
          "proposalName": String,
          "filePath": String,
          "abstractPath": String,
          "topics":
          [
            "unu",
            "dou"
          ],
          "keywords":
          [
            "unu",
            "doi"
          ],
          "coAuthors":
          [
            "email1",
            "email2"
          ],
          "bidders":
          [
            "email1",
            "email2"
          ],
          "reviewers":
          [
            "email1",
            "email2"
          ],
          "reviews":
          [
            //can be empty
            {
              "email": String,
              "proposal":
              {
                "proposalName": String,
                "author": "email"
              }
            }
          ]
        }
      response:
        STATUS: 200 or 400*/

    addProposal : function (conferenceid: number, author : String, proposalName : String, filePath : String, abstractPath : String, topics : String[], keywords: String[], coAuthors : String[], bidders: String[], reviewers: String[], reviews: Review[]) {

        return request({

            method : "POST",
            url : `${domain}/api/conferences/${conferenceid}/proposals`,
            json : true,
            body : {
                author : author,
                proposalName : proposalName,
                filePath : filePath,
                abstractPath : abstractPath,
                topics : topics,
                keywords : keywords,
                coAuthors : coAuthors,
                bidders : bidders,
                reviewers : reviewers,
                reviews : reviews
            }
        })
    },


    /* remove proposal
      /api/conferences/{conferenceid}/proposals
      DELETE
      body:
        {
          "proposalName": String,
          "author": "email"
        }
      response:
        STATUS: 200 or 400*/

    removeProposal : function(conferenceid: number, proposalName: String, author: String) {

        return request({
            "method" : "DELETE",
            "url" : `${domain}/api/conferences/${conferenceid}/proposals`,
            json : true,
            body : {
                proposalName : proposalName,
                author : author
            }

        })
    },

    /*get proposals
      /api/conferences/{conferenceid}/proposals
      GET
      body: N/A
      response:
        [
          {
            "author": "email",
            "proposalName": String,
            "filePath": String,
            "abstractPath": String,
            "topics":
            [
              "unu",
              "dou"
            ],
            "keywords":
            [
              "unu",
              "doi"
            ],
            "coAuthors":
            [
              "email1",
              "email2"
            ],
            "bidders":
            [
              "email1",
              "email2"
            ],
            "reviewers":
            [
              "email1",
              "email2"
            ],
            "reviews":
            [
              //can be empty
              {
                "email": String,
                "proposal":
                {
                  "proposalName": String,
                  "author": "email"
                }
              }
            ]
          }
        ]*/

    getProposals : function (conferenceid: number) {

        return request({
            "method" : "GET",
            "url" : `${domain}/api/conferences/${conferenceid}/proposals`
        })
    },

    /*add review
      /api/conferences/{conferenceid}/proposals/{proposalid}/reviews
      POST
      body:
        {
          "email": String,
          "proposal":
          {
            "proposalName": String,
            "author": "email"
          }
        }
      response:
        STATUS: 200 or 400*/


    addReview : function (conferenceid : number, proposalid: number, email: String, proposal: Proposal) {

        return request({
            "method" : "POST",
            "url" : `${domain}/api/conferences/${conferenceid}/proposals/${proposalid}/reviews`,
            json : true,
            body : {
                email : email,
                proposal : proposal
            }
        })
    },


    /* get reviews
      /api/conferences/{conferenceid}/proposals/{proposalid}/reviews
      GET
      body: N/A
      response:
        body:
          [
            {
              "email": String,
              "proposal":
              {
                "proposalName": String,
                "author": "email"
              }
            }
          ]*/


    getReviews : function(conferenceid : number, proposalid : number) {

        return request({
            "method" : "GET",
            "url" : `${domain}/api/conferences/${conferenceid}/proposals/${proposalid}/reviews`
        })
    },

    /* bid
      /api/conferences/{conferenceid}/proposals/{proposalid}/bid
      PUT
      body:
        {
          "email": String
        }
      response:
        STATUS: 200 or 400
*/

    bid : function(conferenceid : number, proposalid : number, email: String) {

        return request({
            "method" : "PUT",
            "url" : `${domain}/api/conferences/${conferenceid}/proposals/${proposalid}/bid`,
            json : true,
            body : {
                email : email
            }

        })
    },

    /* get bidders
      /api/conferences/{conferenceid}/proposals/{proposalid}/bid
      GET
      body: N/A
      response:
        body:
          [
            {
              "firstname": string,
              "lastname": string,
              "email": string,
            }
          ]
*/

    getBidders : function(conferenceid : number, proposalid: number) {

        return request({
            "method" : "GET",
            "url" : `${domain}/api/conferences/${conferenceid}/proposals/${proposalid}/bid`
        })
    }






}

export default proposalsService;
