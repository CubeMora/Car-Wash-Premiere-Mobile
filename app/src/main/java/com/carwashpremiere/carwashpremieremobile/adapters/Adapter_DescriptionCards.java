//package com.carwashpremiere.carwashpremieremobile;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class Adapter_DescriptionCards extends RecyclerView.Adapter<Adapter_DescriptionCards.DescriptionCardsViewHolder> {
//    private Context mContext;
//    private List<Model_CarTypes> mCarTypesList;
//
//    public Adapter_DescriptionCards(Context mContext, List<Model_CarTypes> mCarTypesList) {
//        this.mContext = mContext;
//        this.mCarTypesList = mCarTypesList;
//    }
//
//    @NonNull
//    @Override
//    public DescriptionCardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_description_cards, parent, false);
//        return new DescriptionCardsViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DescriptionCardsViewHolder holder, int position) {
//        Model_CarTypes carType = mCarTypesList.get(position);
//        holder.txt_TitleAutoDescription.setText(carType.getTitle());
//        // Aquí puedes seguir asignando otros valores a las vistas según las propiedades de Model_CarTypes
//    }
//
//    @Override
//    public int getItemCount() {
//        return mCarTypesList.size();
//    }
//
//    public static class DescriptionCardsViewHolder extends RecyclerView.ViewHolder {
//        TextView txt_TitleAutoDescription;
//
//        public DescriptionCardsViewHolder(View itemView) {
//            super(itemView);
//            txt_TitleAutoDescription = itemView.findViewById(R.id.txt_TitleAutoDescription);
//        }
//    }
//}
