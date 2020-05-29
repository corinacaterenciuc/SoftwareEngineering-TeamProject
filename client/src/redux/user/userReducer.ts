// @ts-nocheck
import {User} from "../entities";
import {GET_USERS, REMOVE_USER, UPDATE_USER} from './userActions'

type UserState = { users: User[] };
type Action = { type: string, payload: object }

const initState: UserState = {
    users:
        [
            {
                email: "delaneyryan@undertap.com",
                firstname: "Rosella",
                lastname: "Watson"
            },
            {
                email: "rosellawatson@undertap.com",
                firstname: "Drake",
                lastname: "Lawson"
            },
            {
                email: "drakelawson@undertap.com",
                firstname: "Mabel",
                lastname: "Thompson"
            },
            {
                email: "mabelthompson@undertap.com",
                firstname: "Tameka",
                lastname: "Marshall"
            },
            {
                email: "tamekamarshall@undertap.com",
                firstname: "Pugh",
                lastname: "English"
            },
            {
                email: "pughenglish@undertap.com",
                firstname: "Amie",
                lastname: "Brewer"
            },
            {
                email: "amiebrewer@undertap.com",
                firstname: "Rios",
                lastname: "Avila"
            },
            {
                email: "riosavila@undertap.com",
                firstname: "Rosario",
                lastname: "Good"
            },
            {
                email: "rosariogood@undertap.com",
                firstname: "Eunice",
                lastname: "House"
            },
            {
                email: "eunicehouse@undertap.com",
                firstname: "Patricia",
                lastname: "Anderson"
            },
            {
                email: "patriciaanderson@undertap.com",
                firstname: "Schroeder",
                lastname: "Dalton"
            },
            {
                email: "schroederdalton@undertap.com",
                firstname: "Bryan",
                lastname: "Lucas"
            },
            {
                email: "bryanlucas@undertap.com",
                firstname: "Hazel",
                lastname: "Rosario"
            },
            {
                email: "hazelrosario@undertap.com",
                firstname: "Carissa",
                lastname: "Hobbs"
            },
            {
                email: "carissahobbs@undertap.com",
                firstname: "Ladonna",
                lastname: "Whitfield"
            },
            {
                email: "ladonnawhitfield@undertap.com",
                firstname: "Bernard",
                lastname: "Travis"
            },
            {
                email: "bernardtravis@undertap.com",
                firstname: "Judith",
                lastname: "Patel"
            },
            {
                email: "judithpatel@undertap.com",
                firstname: "Mccoy",
                lastname: "Savage"
            },
            {
                email: "mccoysavage@undertap.com",
                firstname: "Mcgowan",
                lastname: "Garrett"
            },
            {
                email: "mcgowangarrett@undertap.com",
                firstname: "Ashlee",
                lastname: "Small"
            },
            {
                email: "ashleesmall@undertap.com",
                firstname: "Stanton",
                lastname: "Cole"
            },
            {
                email: "stantoncole@undertap.com",
                firstname: "Reyes",
                lastname: "Morse"
            },
            {
                email: "reyesmorse@undertap.com",
                firstname: "Frank",
                lastname: "Beach"
            },
            {
                email: "frankbeach@undertap.com",
                firstname: "Jeanne",
                lastname: "Velasquez"
            },
            {
                email: "jeannevelasquez@undertap.com",
                firstname: "Nunez",
                lastname: "Ortega"
            },
            {
                email: "nunezortega@undertap.com",
                firstname: "Susanne",
                lastname: "Herman"
            },
            {
                email: "susanneherman@undertap.com",
                firstname: "Mae",
                lastname: "Jefferson"
            },
            {
                email: "maejefferson@undertap.com",
                firstname: "Pauline",
                lastname: "Donovan"
            },
            {
                email: "paulinedonovan@undertap.com",
                firstname: "Houston",
                lastname: "Poole"
            },
            {
                email: "houstonpoole@undertap.com",
                firstname: "Madeline",
                lastname: "Raymond"
            },
            {
                email: "madelineraymond@undertap.com",
                firstname: "Rocha",
                lastname: "Olson"
            },
            {
                email: "rochaolson@undertap.com",
                firstname: "Dionne",
                lastname: "Black"
            },
            {
                email: "dionneblack@undertap.com",
                firstname: "Stone",
                lastname: "Pope"
            },
            {
                email: "stonepope@undertap.com",
                firstname: "Hunt",
                lastname: "Greene"
            }
        ]
};

export default function (state: UserState = initState, action: Action) {
    let newState: UserState = {users: []};
    let {type, payload} = action;
    switch (type) {
        case REMOVE_USER: {
            newState = {...state};
            newState.users = newState.users.filter(u => u.email !== payload.email);
            break;
        }
        case GET_USERS: {
            newState = {...state};
            newState.users = payload.users;
            break;
        }
        case UPDATE_USER: {
            newState = {...state};
            newState.users = newState.users.map(u => u.email === payload.user.email ? payload.user : u);
            break;
        }
        default:
            return state;
    }
    return newState;
}
