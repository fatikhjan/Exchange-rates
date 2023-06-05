package uz.pdp.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Valuta {
    @SerializedName("Ccy")
    private String ccy;
    @SerializedName("Date")
    private String date;
    @SerializedName("Rate")
    private double rate;

}
