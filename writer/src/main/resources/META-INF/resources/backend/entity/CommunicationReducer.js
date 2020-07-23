export const RESPONSE_ARRIVED = "RESPONSE_ARRIVED";
export const REQUEST_STARTED = "REQUEST_STARTED";


export const communicationReducer = (state = {}, action) => {
    const { type,payload } = action;
    switch (type) {

        case REQUEST_STARTED: {
            return {
                ...state,
                    inProgress: true
            }
        }
        case RESPONSE_ARRIVED: {
            return {
                ...state,
                    inProgress: false,
                    success: payload
            }
        }

    }
    return state;
}